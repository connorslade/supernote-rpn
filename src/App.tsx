import React from 'react';
import {
  StatusBar,
  StyleSheet,
  Text,
  View,
  Pressable,
} from 'react-native';
import { PluginManager } from 'sn-plugin-lib';

function App(): React.JSX.Element {
  return (
    <View style={styles.container}>
      <Pressable style={styles.closeButton} onPress={() => PluginManager.closePluginView()}>
        <Text style={[styles.closeText]}>✕</Text>
      </Pressable>
      <StatusBar/>
      <Text style={[styles.helloText]}>
        Hello World
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#ffffff',
  },
  closeButton: {
    position: 'absolute',
    top: 12,
    right: 12,
    paddingHorizontal: 10,
    paddingVertical: 6,
    borderRadius: 16,
  },
  closeText: {
    fontSize: 18,
    fontWeight: '600',
  },
  helloText: {
    fontSize: 24,
    fontWeight: '600',
    textAlign: 'center',
  },
});

export default App;
